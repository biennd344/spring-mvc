package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        // Lấy sản phẩm, xử lý trường hợp không tìm thấy
        java.util.Optional<Product> productOptional = this.productService.fetchProductById(id);
        if (productOptional.isEmpty()) {
            // Xử lý khi không tìm thấy sản phẩm, ví dụ: chuyển hướng hoặc trả về lỗi
            return "redirect:/admin/product"; // Chuyển về danh sách sản phẩm
        }
        Product pr = productOptional.get();
        model.addAttribute("product", pr);

        return "client/product/detail";
    }

}
