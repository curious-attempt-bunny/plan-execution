package bot.metadata

class Location {
	def name
	def dirs = [:]
	def obstacle = [:]
	
	def add(direction, location) {
		dirs[direction] = location
	}
	
	def addDoor(door, direction, location) {
		add(direction, location)
		obstacle[direction] = door
	}
	
	def eachOpenDir(state, closure) {
		dirs.each { dir, loc ->
			if (!obstacle[dir]) {
				closure(dir, loc)
			} else if (obstacle[dir].isOpen(state)) {
				closure(dir, loc)
			} else if (obstacle[dir].isOpenable(state)) {
				closure('open door', loc)
			}
		}
	}
	
	def isOpenableDoor(state) {
		def result = false
		
		dirs.each { dir, loc ->
			if (obstacle[dir] && obstacle[dir].isOpenable(state)) {
				result = true
			}
		}
		
		return result
	}
	
	def openDoor(state) {
        dirs.each { dir, loc ->
            if (obstacle[dir] && obstacle[dir].isOpenable(state)) {
            	obstacle[dir].open(state)
            }
        }
    }
	
	def eachSolvableBlockedDir(state, closure) {
        dirs.each { dir, loc ->
            if (!obstacle[dir]) {
                // No Action
            } else if (obstacle[dir].isOpen(state)) {
                // No Action
            } else if (obstacle[dir].isOpenable(state)) {
                // No Action
            } else {
            	def requiredObject = obstacle[dir].getRequiredObject(state)
            	closure('open door', loc, requiredObject)
            }
        }
    }
	
	def isPassable(state, dir) {
		if (dirs[dir] && (!obstacle[dir] || obstacle[dir].isOpen(state))) {
			return true
		}
		
		return false
	}
	
	def getDestination(dir) {
		dirs[dir]
	}
	
	def String toString() {
		name
	}
}