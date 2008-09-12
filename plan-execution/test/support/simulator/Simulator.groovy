package support.simulator

class Simulator {
	def plan
	def world
	
	def run(state) {
		println "Running"
		while(!plan.isComplete(state)) {
			def nextState = state.copy()
			
			def cmd = plan.getCommand(world, state)
			println "$state.location: $cmd"
			if (cmd ==~ /get \w+/) {
				def objName = ((cmd =~ /get (\w+)/)[0][1])
				def obj = state.getObject(objName)
				assert obj
				nextState.move(obj, state.location, state.getObject('self'))
			} else if (state.location.isPassable(state, cmd)) {
				nextState.location = state.location.getDestination(cmd)
			} else if (cmd == 'open door' && state.location.isOpenableDoor(state)) {
				state.location.openDoor(nextState)
			}
			
			state = nextState
		}
		
		return state
	}
}