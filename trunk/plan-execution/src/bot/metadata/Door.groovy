package bot.metadata

class Door {
	def isOpen(state) {
		state.obstacleState[this] == 'open'
	}
	
	def isOpenable(state) {
        if (state.obstacleState[this] == 'locked' && !state.carrying.find { it.name == 'key' }) {
        	return false
        }
        
        return true
    }
	
	def getRequiredObject(state) {
		if (state.obstacleState[this] == 'locked') {
			return state.getObject('key')
		}
	}
}