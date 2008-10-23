package bot.plan

class GenericPlan {
	def target
	def criteria
	
	def isComplete(state) {
		criteria.isComplete(state)
	}
	
	def getCommand(world, state, alreadyRequiredObjects) {
		if (criteria.isTargetLocation(state, state.location)) {
			return criteria.getTargetLocationCommand(state)
		}

		def kb = [:]
		def toExplore = []
		def planReq = [:]
		
		kb[state.location] = null
		toExplore << state.location
		
		def resultDir = null
		def resultPlan = null
		
		while(toExplore && !resultDir) {
			def exploreFrom = toExplore.remove(0)
			
			exploreFrom.eachOpenDir(state) { dir, loc ->
				if (!kb.containsKey(loc)) {
					kb[loc] = kb[exploreFrom] ?: dir
                    planReq[loc] = planReq[exploreFrom]
					
					if (criteria.isTargetLocation(state, loc)) {
	                    resultDir = kb[loc]
	                    resultPlan = planReq[loc]
	                } else {
	                	toExplore << loc
	                }
				}
	        }
			
			exploreFrom.eachSolvableBlockedDir(state) { dir, loc, requiredObject -> 
				if (!kb.containsKey(loc) && !(requiredObject in alreadyRequiredObjects)) {
					planReq[loc] = planReq[exploreFrom] ?: new GetPlan(target:requiredObject)
					kb[loc] = kb[exploreFrom] ?: dir

					if (criteria.isTargetLocation(state, loc)) {
	                    resultDir = kb[loc]
	                    resultPlan = planReq[loc]
	                } else {
	                    toExplore << loc
	                }
	            }
			}
		}
		
		if (resultPlan != null) {
			def requiredObjects = []
			requiredObjects.addAll(alreadyRequiredObjects)
			requiredObjects.add(resultPlan.target)
			resultDir = resultPlan.getCommand(world, state, requiredObjects)
		}
		
		if (resultDir == null) {
			int x = 4;
		}
		
		return resultDir
	}
}