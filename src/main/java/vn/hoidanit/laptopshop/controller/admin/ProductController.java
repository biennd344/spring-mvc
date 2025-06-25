package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> prs = this.productService.fetchProducts();
        model.addAttribute("products", prs);
        return "admin/product/show";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(
            @ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult, @RequestParam("binFile") MultipartFile file) {
        if (newProductBindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        String image = this.uploadService.handleSaveUploadFile(file, "product");
        pr.setImage(image);
        this.productService.createProduct(pr);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        // Lấy sản phẩm dựa trên id để hiển thị thông tin (tùy chọn)
        java.util.Optional<Product> productOptional = this.productService.fetchProductById(id);
        if (productOptional.isEmpty()) {
            return "redirect:/admin/product"; // Nếu không tìm thấy, quay lại danh sách
        }
        Product product = productOptional.get();
        model.addAttribute("newProduct", product); // Truyền sản phẩm có id
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping(value = "/admin/product/delete")
    public String postDeleteProductPage(Model model, @ModelAttribute("newProduct") Product pr) {
        this.productService.deleteProduct(pr.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        // Lấy sản phẩm, xử lý trường hợp không tìm thấy
        java.util.Optional<Product> productOptional = this.productService.fetchProductById(id);
        if (productOptional.isEmpty()) {
            // Xử lý khi không tìm thấy sản phẩm, ví dụ: chuyển hướng hoặc trả về lỗi
            return "redirect:/admin/product"; // Chuyển về danh sách sản phẩm
        }
        Product pr = productOptional.get();
        model.addAttribute("product", pr);

        return "admin/product/detail";
    }
    // @GetMapping("/admin/product/{id}")
    // public String getUserDeteilPage(Model model, @PathVariable long id) {
    // Product pr = this.productService.fetchProductById(id).get();
    // model.addAttribute("product", pr);
    // model.addAttribute("id", id);

    // return "admin/product/detail";
    // }
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        if (currentProduct.isEmpty()) {
            return "redirect:/admin/product"; // Nếu không tìm thấy, quay lại danh sách
        }
        model.addAttribute("newProduct", currentProduct.get());
        return "admin/product/update";
    }

    @PostMapping(value = "/admin/product/update")
    public String postUpdateUserPage(Model model, @ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("binFile") MultipartFile file) {
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Optional<Product> currentProductOptional = this.productService.fetchProductById(pr.getId());
        if (currentProductOptional.isEmpty()) {
            return "redirect:/admin/product"; // Nếu không tìm thấy, quay lại danh sách
        }
        Product currentProduct = currentProductOptional.get();

        if (!file.isEmpty()) {
            String img = this.uploadService.handleSaveUploadFile(file, "product");
            currentProduct.setImage(img);
        }
        currentProduct.setName(pr.getName());
        currentProduct.setPrice(pr.getPrice());
        currentProduct.setQuantity(pr.getQuantity());
        currentProduct.setDetailDesc(pr.getDetailDesc());
        currentProduct.setShortDesc(pr.getShortDesc());
        currentProduct.setFactory(pr.getFactory());
        currentProduct.setTarget(pr.getTarget());

        this.productService.createProduct(currentProduct);
        return "redirect:/admin/product";
    }
    // @GetMapping("/admin/product/update/{id}")
    // public String getUpdateUserPage(Model model, @PathVariable long id) {
    // Optional<Product> currentProduct = this.productService.fetchProductById(id);

    // model.addAttribute("newProduct", currentProduct.get());
    // // model.addAttribute("id", id);

    // return "admin/product/update";
    // }

    // @PostMapping(value = "/admin/product/update")
    // public String postUpdateUserPage(Model model, @ModelAttribute("newProduct")
    // @Valid Product pr,
    // BindingResult newProductBindingResult,
    // @RequestParam("binFile") MultipartFile file) {
    // if (newProductBindingResult.hasErrors()) {
    // return "admin/product/update";
    // }

    // Product currentProduct =
    // this.productService.fetchProductById(pr.getId()).get();
    // if (currentProduct != null) {
    // if (!file.isEmpty()) {
    // String img = this.uploadService.handleSaveUploadFile(file, "product");
    // currentProduct.setImage(img);
    // }
    // currentProduct.setName(pr.getName());
    // currentProduct.setPrice(pr.getPrice());
    // currentProduct.setQuantity(pr.getQuantity());
    // currentProduct.setDetailDesc(pr.getDetailDesc());
    // currentProduct.setShortDesc(pr.getShortDesc());
    // currentProduct.setFactory(pr.getFactory());
    // currentProduct.setTarget(pr.getTarget());

    // this.productService.createProduct(currentProduct);
    // }
    // return "redirect:/admin/product";
    // }

}
