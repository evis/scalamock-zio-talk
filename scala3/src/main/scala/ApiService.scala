import zio._

class ApiService(userService: UserService) {
  def getGreeting(userId: Int): UIO[String] =
    for {
      userName <- userService.getUserName(userId)
    } yield s"Hello, $userName!"
}
