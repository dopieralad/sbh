package pl.dopierala.bio.sbh.solution.configuration

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration
@EnableConfigurationProperties(SolutionProperties::class)
class SolutionConfiguration {

    @Bean
    fun dispatcher(): CoroutineDispatcher {
        val availableProcessors = Runtime.getRuntime()?.availableProcessors() ?: 4
        val executor = Executors.newFixedThreadPool(availableProcessors)
        return executor.asCoroutineDispatcher()
    }
}
