package com.labs2160.slacker.core.dao
import com.labs2160.slacker.api.Action
/**
 * Created by mdometita on 8/21/15.
 */
trait ActionDAO {
   def listActionClasses(): List[Class[_ <: Action]]
}
