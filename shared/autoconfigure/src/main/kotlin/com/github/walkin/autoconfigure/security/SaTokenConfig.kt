package com.github.walkin.autoconfigure.security

//import cn.dev33.satoken.filter.SaServletFilter
//import cn.dev33.satoken.jwt.StpLogicJwtForStateless
//import cn.dev33.satoken.stp.StpLogic
//import cn.dev33.satoken.stp.StpUtil
//import cn.dev33.satoken.util.SaResult
//import com.github.walkin.autoconfigure.SecurityConfig.Companion.EXCLUDED_PATHS
//import org.slf4j.kotlin.getLogger
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//@ConditionalOnClass(name = ["cn.dev33.satoken.filter.SaServletFilter"])
//class SaTokenConfig {
//
//  val logger by getLogger()
//
//  @Bean
//  fun getStpLogicJwt(): StpLogic {
//    return StpLogicJwtForStateless()
//  }
//
//  @Bean
//  fun saReactorFilter(): SaServletFilter =
//    SaServletFilter()
//      // 指定 [拦截路由]（此处为拦截所有path）
//      .addInclude("/**")
//      // 指定 [放行路由]
//      .addExclude(*EXCLUDED_PATHS)
//      // 指定[认证函数]: 每次请求执行
//      .setAuth { StpUtil.checkLogin() }
//      // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
//      .setError { e: Throwable ->
//        logger.error(e.message ?: e.toString(), e)
//        SaResult.error(e.message)
//      }
//}
