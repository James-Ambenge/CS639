
package example.myapp


interface FishAction  {
    fun eat()
}
abstract class AquariumFish {
    abstract val color: String
}
/*

class Shark: AquariumFish() {
    override val color = "grey"
}

class Plecostomus: AquariumFish() {
    override val color = "gold"
}
*/
/*

class Shark: AquariumFish(), FishAction {
    override val color = "grey"
    override fun eat() {
        println("hunt and eat fish")
    }
}
*/

/*

class Plecostomus: AquariumFish(), FishAction {
    override val color = "gold"
    override fun eat() {
        println("eat algae")
    }
}
*/


interface FishColor {
    val color: String
}

/*

class Plecostomus: FishAction, FishColor {
    override val color = "gold"
    override fun eat() {
        println("eat algae")
    }
}
*/


class Shark: FishAction, FishColor {
    override val color = "grey"
    override fun eat() {
        println("hunt and eat fish")
    }
}

object GoldColor : FishColor {
    override val color = "gold"
}

/*

class Plecostomus:  FishAction, FishColor by GoldColor {
    override fun eat() {
        println("eat algae")
    }
}

*/
/*

class Plecostomus(fishColor: FishColor = GoldColor):  FishAction,
    FishColor by fishColor {
    override fun eat() {
        println("eat algae")
    }
}

*/

class PrintingFishAction(val food: String) : FishAction {
    override fun eat() {
        println(food)
    }
}

class Plecostomus (fishColor: FishColor = GoldColor):
    FishAction by PrintingFishAction("eat algae"),
    FishColor by fishColor


