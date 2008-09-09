package bot.plan

class Planner {
	def world
	
	def getCommand(state, plan) {
		plan.getCommand(world, state)
	}
}