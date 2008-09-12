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
	
	def open(state) {
		if (isOpenable(state)) {
			state.obstacleState[this] = 'open'
		}
	}
	
	def getRequiredObject(state) {
		if (state.obstacleState[this] == 'locked') {
			return state.getObject('key')
		}
	}
}