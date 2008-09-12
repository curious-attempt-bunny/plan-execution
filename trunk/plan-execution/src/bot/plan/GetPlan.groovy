package bot.plan

class GetPlan {
	def target
	
	def isComplete(state) {
		return target in state.carrying
	}
	
	def getCommand(world, state) {
		getCommand(world, state, [])
	}
	
	def getCommand(world, state, alreadyRequiredObjects) {
		if (target in state.getContents(state.location)) {
			return "get $target.name"
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
					
					if (target in state.getContents(loc)) {
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

					if (target in state.getContents(loc)) {
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
		
		return resultDir
	}
}