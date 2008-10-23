package bot.plan

class GetPlan {
	def target
	def plan
	
	GetPlan() {
		plan = new GenericPlan(criteria:this)
	}
	
	def getCommand(world, state) {
        getCommand(world, state, [])
    }
	
	def getCommand(world, state, alreadyRequiredObjects) {
        plan.getCommand(world, state, alreadyRequiredObjects)
    }
	
	def isComplete(state) {
		return (target in state.carrying)
	}
	
	def isTargetLocation(state, location) {
		return (target in state.getContents(location))
	}
	
	def getTargetLocationCommand(state) {
		return "get $target.name"
	}
}