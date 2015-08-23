import java.nio.file.Paths
import javax.servlet.ServletContext

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSpec, GivenWhenThen}
import org.scalatra.servlet.RichServletContext

/**
 * Created by mdometita on 8/21/15.
 */
class ScalatraBootstrapSpec extends FunSpec with BeforeAndAfter with GivenWhenThen with MockFactory {

    val bootstrap = new ScalatraBootstrap
    describe("ScalatraBootstrap") {
        describe("#loadLibraries") {
            it("loads it corrrectly") {

                bootstrap.loadLibraries(Paths.get("/Users/mdometita/Developer/Slacker/current/lib"))
            }
        }
    }
}
