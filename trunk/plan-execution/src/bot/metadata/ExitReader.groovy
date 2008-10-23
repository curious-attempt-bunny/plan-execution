package bot.metadata

public class ExitReader{
	def init(world) {
		getClass().getResourceAsStream('exits.map').eachLine { line ->
			if (line.startsWith('#')) return
			
			def p = line.split("=")
		    assert p.length == 2
		    
		    def p2 = p[0].split("\\.")
		    assert p2.length == 2
		    
		    def loc = p2[0].trim()
		    def dir = p2[1].trim()
		    def target = p[1].trim()
		    
			def locA = world.getObject(loc)
			if (!locA) {
				locA = new Location(name:loc)
				world << locA
			}
			def locB = world.getObject(target)
            if (!locB) {
                locB = new Location(name:target)
                world << locB
            }
			locA.add(dir, locB)
		}
	}
}
