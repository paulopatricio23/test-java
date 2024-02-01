package br.com.blz.testjava.templates

import br.com.blz.testjava.dto.InventoryDTO
import br.com.blz.testjava.dto.ProdutoDTO
import br.com.blz.testjava.model.Inventory
import br.com.blz.testjava.model.Produto
import br.com.blz.testjava.model.Warehouse
import br.com.blz.testjava.model.WarehouseTypeEnum

class TemplatesMock {

  companion object {

    fun getProdutoDTOMock(quantity: Int): ProdutoDTO {
      return ProdutoDTO(
        name = "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
        inventoryDTO = getInventoryDTOMock(quantity)
      )
    }

    private fun getInventoryDTOMock(quantity: Int): InventoryDTO {
      return InventoryDTO(
        warehouses = listOf(getWarehouseMock(quantity))
      )
    }

    private fun getWarehouseMock(quantity: Int): Warehouse {
      return Warehouse(
        locality = "SP",
        quantity = quantity,
        type = WarehouseTypeEnum.ECOMMERCE
      )
    }

    fun getProdutoEmEstoqueMock(sku: Long): Produto {
      return Produto (
        sku = sku,
        name = "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
        inventory = getInventoryMock(),
        isMarketable = true
      )
    }

    private fun getInventoryMock(): Inventory {
      return Inventory(
        quantity = 12,
        warehouses = listOf(getWarehouseMock(12))
      )
    }
  }
}
