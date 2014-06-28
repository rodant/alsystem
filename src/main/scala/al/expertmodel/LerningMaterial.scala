package al.expertmodel

import scala.concurrent.duration.Duration

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:59
 */
case class LearningMaterial(duration: Duration, required: Boolean, content: Content)

abstract case class Content()
