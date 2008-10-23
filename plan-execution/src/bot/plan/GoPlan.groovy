package bot.plan

class GoPlan {
	def target
	def plan
    
	GoPlan() {
        plan = new GenericPlan(criteria:this)
    }
    
    def getCommand(world, state) {
        getCommand(world, state, [])
    }
    
    def getCommand(world, state, alreadyRequiredObjects) {
        plan.getCommand(world, state, alreadyRequiredObjects)
    }
    
    def isComplete(state) {
        return (state.location == target)
    }
    
    def isTargetLocation(state, location) {
    	return (location == target)
    }
    
    def getTargetLocationCommand(state) {
        return null
    }
	
}