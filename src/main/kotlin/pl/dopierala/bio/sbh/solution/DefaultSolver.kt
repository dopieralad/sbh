package pl.dopierala.bio.sbh.solution

import kotlinx.coroutines.*
import org.springframework.stereotype.Component
import pl.dopierala.bio.sbh.solution.configuration.SolutionProperties
import pl.dopierala.bio.sbh.solution.graph.Graph
import pl.dopierala.bio.sbh.solution.graph.GraphFactory
import pl.dopierala.bio.sbh.solution.model.Instance
import pl.dopierala.bio.sbh.solution.model.Sequence
import java.lang.Exception

@Component
class DefaultSolver(private val graphFactory: GraphFactory, private val solutionProperties: SolutionProperties, private val dispatcher: CoroutineDispatcher) : Solver {

    override fun solve(instance: Instance): String {
        val spectrumGraph = graphFactory.create(instance.spectrum)
        val pheromoneGraph = HashMap<String, HashMap<String, HashMap<Int, Double>>>()
        val bestSequence = getBestSequence(instance, spectrumGraph, pheromoneGraph)

        return bestSequence.get()
    }

    private fun getBestSequence(instance: Instance, spectrumGraph: Graph, pheromoneGraph: HashMap<String, HashMap<String, HashMap<Int, Double>>>): Sequence {
        return (0 until solutionProperties.generations)
                .flatMap {
                    println("Generation: '$it'.")

                    val sequences = generateSequences(instance, spectrumGraph, pheromoneGraph)

                    evaporatePheromone(pheromoneGraph)

                    applyPheromone(pheromoneGraph, sequences)

                    return@flatMap sequences
                }
                .maxBy(Sequence::value)!!
    }

    private fun generateSequences(instance: Instance, spectrumGraph: Graph, pheromoneGraph: HashMap<String, HashMap<String, HashMap<Int, Double>>>): List<Sequence> {
        return runBlocking(dispatcher) {
            (0 until solutionProperties.ants)
                    .map { async { generateSequence(instance, spectrumGraph, pheromoneGraph) } }
                    .awaitAll()
        }
    }

    private fun generateSequence(instance: Instance, spectrumGraph: Graph, pheromoneGraph: HashMap<String, HashMap<String, HashMap<Int, Double>>>): Sequence {
        try {
            val firstOligonucleotide = instance.firstOligonucleotide()
            val currentSequence = Sequence().apply { add(firstOligonucleotide) }
            var currentNucleotide = firstOligonucleotide

            val getPheromoneForTarget: (Pair<String, Int>) -> Double = { target ->
                pheromoneGraph
                        .getOrDefault(currentNucleotide, emptyMap<String, HashMap<Int, Double>>())
                        .getOrDefault(target.first, emptyMap<Int, Double>())
                        .getOrDefault(target.second, 0.1)
            }
            val getNextNucleotideValue: (Pair<String, Int>) -> Double = { pair -> getPheromoneForTarget(pair) * pair.second }

            while (currentSequence.length() < instance.dnaLength) {
                val potentialSuccessors = extractPotentialSuccessors(instance, currentNucleotide, currentSequence, spectrumGraph, getNextNucleotideValue)

                val (oligonucleotide, overlap) = chooseRandomNucleotide(potentialSuccessors, getNextNucleotideValue)

                currentSequence.add(oligonucleotide, overlap)
                currentNucleotide = oligonucleotide
            }

            return currentSequence
        } catch (e: Exception) {
            return Sequence()
        }
    }

    private fun extractPotentialSuccessors(instance: Instance, currentNucleotide: String, currentSequence: Sequence, spectrumGraph: Graph, getValue: (Pair<String, Int>) -> Double): List<Pair<String, Int>> {
        val potentialNextNucleotides = spectrumGraph[currentNucleotide] ?: throw IllegalStateException()
        return potentialNextNucleotides.entries
                .flatMap { entry -> entry.value.map { value -> entry.key to value } }
                .filter { currentSequence.length() + it.first.length - it.second <= instance.dnaLength }
                .sortedByDescending(getValue)
    }

    private fun chooseRandomNucleotide(nucleotides: List<Pair<String, Int>>, getValue: (Pair<String, Int>) -> Double): Pair<String, Int> {
        val totalValue = nucleotides.map { getValue(it) }.sum()
        val randomValue = Math.random() * totalValue
        var currentValue = 0.0

        return if (totalValue > 0) nucleotides.first {
            val value = getValue(it)
            currentValue += value
            return@first currentValue >= randomValue
        } else {
            nucleotides.random()
        }
    }

    private fun evaporatePheromone(pheromoneGraph: HashMap<String, HashMap<String, HashMap<Int, Double>>>) {
        pheromoneGraph.forEach { source ->
            source.value.forEach { target ->
                target.value.replaceAll { _, value ->
                    (1.0 - solutionProperties.pheromone.evaporation) * value
                }
            }
        }
    }

    private fun applyPheromone(pheromoneGraph: HashMap<String, HashMap<String, HashMap<Int, Double>>>, sequences: Collection<Sequence>) {
        sequences.forEach { sequence ->
            val pheromone = sequence.value()

            sequence.sequence().windowed(2).forEach {
                val (source) = it[0]
                val (target, overlap) = it[1]
                pheromoneGraph
                        .getOrPut(source, { HashMap() })
                        .getOrPut(target, { HashMap() })
                        .compute(overlap) { _, currentPheromone -> (currentPheromone ?: 0.0) + pheromone }
            }
        }
    }
}
