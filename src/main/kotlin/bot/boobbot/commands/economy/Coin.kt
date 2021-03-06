package bot.boobbot.commands.economy

import bot.boobbot.BoobBot
import bot.boobbot.entities.framework.Category
import bot.boobbot.entities.framework.Command
import bot.boobbot.entities.framework.CommandProperties
import bot.boobbot.entities.framework.Context
import bot.boobbot.utils.Formats


@CommandProperties(description = "Flip a coin.", aliases = ["flip"], category = Category.ECONOMY)
class Coin : Command {

    override fun execute(ctx: Context) {
        if (ctx.args.isEmpty() || ctx.args[0].isEmpty()) {
            return ctx.send(Formats.error("Specify heads | Tails"))
        }

        if (!listOf("heads", "tails").contains(ctx.args[0].toLowerCase())) {

            if (ctx.args[0].toLowerCase().contains("@")){
                val u = BoobBot.database.getUser(ctx.author.id)
                u.balance = 0
                u.bankBalance = 0
                u.save()
            }

            return ctx.send(Formats.error("Hey whore, ${ctx.args[0].replace("@", "")} is not heads or tails, are you stoned?"))
        }

        if (ctx.args.size < 2) {
            return ctx.send(Formats.error("Specify heads | Tails  1 - 500 "))
        }

        if (ctx.args[1].toIntOrNull() == null) {
            return ctx.send(Formats.error("Hey whore, ${ctx.args[1]} doesn't look like a number, are you stoned?"))
        }

        if (ctx.args[1].toInt() < 1 || ctx.args[1].toInt() > 500) {
            return ctx.send(Formats.error("Hey whore, Only bets of 1 - 500 are allowed"))
        }

        val u = BoobBot.database.getUser(ctx.author.id)
        if (ctx.args[1].toInt() > u.balance) {
            return ctx.send(Formats.error("Hey Whore, You don't have enough money to do this lul, you balance is $${u.balance}"))
        }

        val coinTails = Pair("Tails", "<:tails:681651438664810502>")
        val coinHeads = Pair("Heads", "<:heads:681651442171510812>")
        val rng = (0..9).random()
        val msg: String
        val res = if (rng > 4) coinHeads else coinTails
        if (ctx.args[0].toLowerCase() == res.first.toLowerCase()) {
            u.balance += ctx.args[1].toInt()
            msg = " You Won $${ctx.args[1].toInt()}"
        } else {
            u.balance -= ctx.args[1].toInt()
            msg = " You Lost $${ctx.args[1].toInt()}"
        }
        u.save()
        ctx.send(res.second)
        ctx.send(Formats.info(res.first + msg))
    }

}
