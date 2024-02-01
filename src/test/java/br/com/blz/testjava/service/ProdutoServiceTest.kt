package br.com.blz.testjava.service

import br.com.blz.testjava.dto.ProdutoDTO
import br.com.blz.testjava.templates.TemplatesMock
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@SpringBootTest
class ProdutoServiceTest {

  @InjectMocks
  private val produtoService = ProdutoService()

  private val sku = 1234L
  private val skuInexistente = 12345L
  private val quantidadeEmEstoque = 12
  private val estoqueZerado = 0

  lateinit var produtoDTO: ProdutoDTO
  lateinit var produtoEstoqueZeradoDTO: ProdutoDTO

  @BeforeEach
  fun beforeEach() {
    produtoDTO = TemplatesMock.getProdutoDTOMock(quantidadeEmEstoque)
    produtoEstoqueZeradoDTO = TemplatesMock.getProdutoDTOMock(estoqueZerado)
  }

  @Test
  fun `cria um produto com estoque`(){
    val produtoCriado = produtoService.create(sku, produtoDTO)

    assertEquals(sku, produtoCriado.sku)
    assertEquals(produtoDTO.name, produtoCriado.name)
    assertEquals(produtoDTO.inventoryDTO.warehouses[0].quantity, produtoCriado.inventory.quantity)
    assertTrue(produtoCriado.isMarketable)
  }

  @Test
  fun `cria um produto com estoque zerado`(){
    val produtoCriado = produtoService.create(sku, produtoEstoqueZeradoDTO)

    assertEquals(sku, produtoCriado.sku)
    assertEquals(produtoEstoqueZeradoDTO.name, produtoCriado.name)
    assertEquals(0, produtoCriado.inventory.quantity)
    assertFalse(produtoCriado.isMarketable)
  }

  @Test
  fun `erro ao criar um produto devido a sku existente`() {
    produtoService.addProdutos(TemplatesMock.getProdutoEmEstoqueMock(1234L))

    assertThrows<Exception> { produtoService.create(sku, produtoDTO) }
  }

  @Test
  fun `buscar um produto em estoque por sku`() {
    adicionarUmProdutoNaLista()

    val produtoEncontrado = produtoService.getProdutoBySku(sku)

    assertEquals(sku, produtoEncontrado.sku)
    assertEquals(quantidadeEmEstoque, produtoEncontrado.inventory.quantity)
    assertTrue(produtoEncontrado.isMarketable)
  }

  @Test
  fun `erro buscar um produto por sku inexistente`() {
    adicionarUmProdutoNaLista()

    assertThrows<Exception> { produtoService.getProdutoBySku(skuInexistente) }
  }

  @Test
  fun `atualizando um produto em estoque`() {
    adicionarUmProdutoNaLista()

    val nomeNovo = "Nome novo produto"
    produtoDTO.name = nomeNovo

    val produtoAlterado = produtoService.update(sku, produtoDTO)

    assertEquals(nomeNovo, produtoAlterado.name)
    assertTrue(produtoAlterado.isMarketable)
  }

  @Test
  fun `zerando um produto no estoque`() {
    adicionarUmProdutoNaLista()

    produtoDTO.inventoryDTO.warehouses[0].quantity = estoqueZerado

    val produtoAlterado = produtoService.update(sku, produtoDTO)

    assertFalse(produtoAlterado.isMarketable)
    assertEquals(estoqueZerado, produtoAlterado.inventory.quantity)
  }

  @Test
  fun `erro ao atualizar um produto devido a sku nao encontrado`() {
    adicionarUmProdutoNaLista()

    val nomeNovo = "Nome novo produto"
    produtoDTO.name = nomeNovo

    assertThrows<Exception> { produtoService.update(skuInexistente, produtoDTO) }
  }

  @Test
  fun `excluir um produto`() {
    adicionarUmProdutoNaLista()

    produtoService.delete(sku)
  }

  @Test
  fun `erro ao excluir um produto devido a sku inexistente`() {
    adicionarUmProdutoNaLista()

    assertThrows<Exception> { produtoService.delete(skuInexistente) }
  }

  private fun adicionarUmProdutoNaLista() {
    val produto = TemplatesMock.getProdutoEmEstoqueMock(sku)
    produtoService.addProdutos(produto)
  }

}
