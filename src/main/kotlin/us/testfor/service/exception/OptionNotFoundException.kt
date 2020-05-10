package us.testfor.service.exception

import java.lang.RuntimeException

class OptionNotFoundException(val id: Int): RuntimeException("Option {$id} not found")