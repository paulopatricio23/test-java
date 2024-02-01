package br.com.blz.testjava.controller

import br.com.blz.testjava.dto.ProdutoDTO
import br.com.blz.testjava.model.Produto
import br.com.blz.testjava.service.ProdutoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/produto")
class ProdutoController (
  private val produtoService: ProdutoService
  ) {

  @GetMapping("/{sku}")
  @ResponseStatus(HttpStatus.OK)
  fun getBySky(@PathVariable(value = "sku") sku: Long): Produto {
    return produtoService.getProdutoBySku(sku)
  }

  @PostMapping("/{sku}")
  @ResponseStatus(HttpStatus.CREATED)
  fun create(@PathVariable(value = "sku") sku: Long, @RequestBody produtoDTO: ProdutoDTO): Produto {
    return produtoService.create(sku, produtoDTO)
  }

  @PutMapping("/{sku}")
  @ResponseStatus(HttpStatus.OK)
  fun update(@PathVariable(value = "sku") sku: Long, @RequestBody produtoDTO: ProdutoDTO): Produto {
    return produtoService.update(sku, produtoDTO)
  }

  @DeleteMapping("/{sku}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun delete(@PathVariable(value = "sku") sku: Long) {
    produtoService.delete(sku)
  }
}
