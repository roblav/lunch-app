package services

import com.google.inject.ImplementedBy
import models.Sandwich

class RealSandwichService extends SandwichService {

  //just an empty list
  //override def sandwiches(): List[Sandwich] = List()

  val ham = Sandwich("Ham and Cheese", 2.99)
  val tuna = Sandwich("Tuna", 2.11)
  override def sandwiches(): List[Sandwich] = List(ham,tuna)
}

@ImplementedBy(classOf[RealSandwichService])
trait SandwichService {
  def sandwiches: List[Sandwich]
}

