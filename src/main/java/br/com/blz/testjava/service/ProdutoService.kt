package br.com.blz.testjava.service

import br.com.blz.testjava.dto.InventoryDTO
import br.com.blz.testjava.dto.ProdutoDTO
import br.com.blz.testjava.model.Inventory
import br.com.blz.testjava.model.Produto
import br.com.blz.testjava.model.Warehouse
import org.springframework.stereotype.Service

@Service
class ProdutoService (

  ) {

  private var produtos = mutableListOf<Produto>()

  fun getProdutoBySku(sku: Long): Produto {
    for (produto in produtos) {
      if (sku == produto.sku) {
        return produto
      }
    }
    throw Exception("Produto não encontrado com o sku: $sku")
  }

  fun create(sku: Long, produtoDTO: ProdutoDTO): Produto {
    validateSku(sku)
    val produto = convertProdutoDTOToProduto(sku, produtoDTO)
    produtos.add(produto)
    return produto
  }

  fun update(sku: Long, produtoDTO: ProdutoDTO): Produto {
    val produto = getProdutoBySku(sku)
    produto.name = produtoDTO.name
    produto.inventory = createInventory(produtoDTO.inventoryDTO)
    produto.isMarketable = verifyIsMarketable(produto.inventory.quantity)
    return produto
  }

  fun delete(sku: Long) {
    val produto = getProdutoBySku(sku)
    produtos.remove(produto)
  }

  private fun validateSku(sku: Long) {
    for (produto in produtos) {
      if (sku == produto.sku) {
        throw Exception("Produto com sku já existente")
      }
    }
  }

  private fun createInventory(inventoryDTO: InventoryDTO): Inventory {
    return Inventory(
        quantity = calculateQuantity(inventoryDTO.warehouses),
        warehouses = inventoryDTO.warehouses
      )
  }

  private fun calculateQuantity(warehouses: List<Warehouse>): Int {
    var quantity = 0
    for (warehouse in warehouses) {
      quantity = quantity.plus(warehouse.quantity)
    }
    return quantity
  }

  private fun verifyIsMarketable(quantity: Int): Boolean {
    return quantity > 0
  }

  private fun convertProdutoDTOToProduto(sku: Long, produtoDTO: ProdutoDTO): Produto {
    val inventory = createInventory(produtoDTO.inventoryDTO)
    val isMarketable = verifyIsMarketable(inventory.quantity)
    return Produto(
      sku = sku,
      name = produtoDTO.name,
      inventory = inventory,
      isMarketable = isMarketable
    )
  }

  fun addProdutos(produto: Produto) {
    produtos.add(produto)
  }
}
