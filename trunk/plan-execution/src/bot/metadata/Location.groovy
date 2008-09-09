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
            	println "Required object from $name to $loc.name is $requiredObject.name"
            	closure('open door', loc, requiredObject)
            }
        }
    }
}