package pl.dopierala.bio.sbh.shell

import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class SbhPromptProvider : PromptProvider {

    override fun getPrompt(): AttributedString {
        val style = AttributedStyle.DEFAULT
                .foreground(AttributedStyle.BLUE)
                .bold()

        return AttributedString("SBH Shell > ", style)
    }
}
