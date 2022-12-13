package org.vliux.nycschools.infra

import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Executors @Inject constructor() {
  open fun defaultExecutor(): Executor = java.util.concurrent.Executors.newCachedThreadPool()
}
