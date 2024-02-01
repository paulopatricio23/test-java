package br.com.blz.testjava.model

class Produto (
  val sku: Long,
  var name: String,
  var inventory: Inventory,
  var isMarketable: Boolean
)
