package bot.metadata

class World {
	def items = [:]
	
	def leftShift(item) {
		items[item.name] = item
	}
	
	def getObject(name) {
		items[name]
	}
}