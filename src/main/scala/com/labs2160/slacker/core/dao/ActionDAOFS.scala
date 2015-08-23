package com.labs2160.slacker.core.dao

import java.nio.file.{Paths, Path}

import com.labs2160.slacker.api.Action

import scala.collection.parallel
import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
 * Created by mdometita on 8/21/15.
 */
class ActionDAOFS(val dir: Path) extends ActionDAO {

    object ActionDAOFS {
        val ActionListFilename = "actions.csv"
    }

    override def listActionClasses(): List[Class[_ <: Action]] = {
        val actionClasses = ListBuffer.empty[Class[_ <: Action]]
        val actionListFile = Paths.get(dir.toString, ActionDAOFS.ActionListFilename)
        Source.fromFile(actionListFile.toFile).getLines().foreach(line => {
            actionClasses += Class.forName(line).asInstanceOf[Class[_ <: Action]]
        })
        actionClasses.toList
    }
}
