package bot.state

import bot.metadata.Door

class State {
	def world
	def location
	def contents = [:]
	def obstacleState = [:]
	
	def put(item, container) {
		contents.get(container, []).add(item)
	}
	
	def getContents(container) {
		contents.get(container, [])
	}
	
	def getCarrying() {
		contents.get('self', [])
	}
	
	def getObject(name) {
		world.getObject(name)
	}
	
	def addClosedDoor(locA, exitA, locB, exitB) {
		def door = addDoor(locA, exitA, locB, exitB)
		obstacleState[door] = 'closed'
	}

    def addLockedDoor(locA, exitA, locB, exitB) {
    	def door = addDoor(locA, exitA, locB, exitB)
        obstacleState[door] = 'locked'
    }

    private def addDoor(locA, exitA, locB, exitB) {
		def door = new Door()
        locA.addDoor(door, exitA, locB)
        locB.addDoor(door, exitB, locA)
        return door
	}
	
}