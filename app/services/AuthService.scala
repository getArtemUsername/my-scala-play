package services

import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.TimeUnit

import model.User
import org.apache.commons.codec.binary.Base64
import play.api.cache.CacheApi
import play.api.mvc.Cookie

import scala.concurrent.duration.Duration

/**
  *
  * AuthService class
  * <p/>
  * Description...
  *
  */
class AuthService(cacheApi: CacheApi) {
  val mda = MessageDigest.getInstance("SHA-512")
  val cookieHeader = "X-Auth-Token"

  def login(userCode: String, password: String): Option[Cookie] = ???

  private def checkUser(userCode: String, password: String): Option[User] = ???

  private def createCookie(user: User): Cookie = {
    val randomPart = UUID.randomUUID().toString.toUpperCase
    val userPart = user.userId.toString.toUpperCase

    val key = s"$randomPart|$userPart"
    val token = Base64.encodeBase64String(mda.digest(key.getBytes))
    val duration = Duration.create(10, TimeUnit.MINUTES)
    cacheApi.set(key, token, duration)
    Cookie(cookieHeader, token, maxAge = Some(duration.toSeconds.toInt))
  }
}
