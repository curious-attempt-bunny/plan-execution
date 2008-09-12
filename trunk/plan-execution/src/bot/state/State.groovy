package bot.state

import bot.metadata.Door

class State {
	def world
	def location
	def contents = [:]
	def obstacleState = [:]
	
	def copy() {
		new State(world:world, location:location, contents:new HashMap(contents), obstacleState:new HashMap(obstacleState))
	}
	
	// allows for an item to be inside multiple containers
	def put(item, container) {
		contents.get(container, []).add(item)
	}
	
	def move(item, fromContainer, toContainer) {
		assert contents[fromContainer].contains(item)
		assert !contents.get(toContainer,[]).contains(item)
        contents[fromContainer].remove(item)
		contents[toContainer].add(item)
	}
	
	def getContents(container) {
		contents.get(container, [])
	}
	
	def getCarrying() {
		contents.get(getObject('self'), [])
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