import zio._

trait UserService {
  def getUserName(id: Int): UIO[String]
}
