package bot.boobbot.commands.nsfw

import bot.boobbot.entities.framework.Category
import bot.boobbot.entities.framework.CommandProperties
import bot.boobbot.entities.framework.BbApiCommand

@CommandProperties(description = "Easter is nice \uD83D\uDC30", nsfw = true, category = Category.HOLIDAY)
class Easter : BbApiCommand("easter")
