public class dog {
	var mood = "HAPPY"
	var energy = 100
	var coordinatePosition = (100,"hola")

	fun Run(): Int {

		MoveForward(2)
	
		var hair = "black"

	
		energy -= 1

	
		mood = "PLAY"

	
		return 0

	}

	fun MoveForward(numbersSteps: Any) {
	
		coordinatePosition[0] += this.energy

	
		mood = "MOVING"

	
		energy -= 1

	}

	fun MoveLeft(numbersSteps: Any) {
	
		coordinatePosition[1] -= this.energy

	
		mood = "MOVING"

	
		energy -= 1

	}

	fun MoveRight(numbersSteps: Any) {
	
		coordinatePosition[1] += this.energy

	
		mood = "MOVING"

	
		energy -= 1

	}

	fun Bark(voice: Any): String {
	
		return "Loud"

	}

}


public class cat {
	var mood = "HAPPY"
	var energy = 100
	var coordenatePosition = (100,"hola")

	fun Run(): Int {

		MoveForward(2)
	
		energy -= 1

	
		mood = "PLAY"

	
		return 0

	}

}


