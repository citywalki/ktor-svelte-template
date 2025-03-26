package com.github.walkin.memos.query

import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDetailsServiceImpl(val userQuery: UserQuery) : ReactiveUserDetailsService {
  override fun findByUsername(username: String?): Mono<UserDetails> {
    return mono {
      userQuery.getUser(FindUser(username)).let {
        User.withUsername(it?.username).password(it?.password).build()
      }
    }
  }
}
