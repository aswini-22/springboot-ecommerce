package com.example.shop.controller;

import com.example.shop.cart.Cart;
import com.example.shop.cart.CartLine;
import com.example.shop.model.CustomerOrder;
import com.example.shop.model.OrderItem;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {

    private final ProductRepository products;
    private final OrderRepository orders;
    private final Cart cart;

    public ShopController(ProductRepository products, OrderRepository orders, Cart cart) {
        this.products = products;
        this.orders = orders;
        this.cart = cart;
    }

    @ModelAttribute("cartCount")
    public int cartCount() {
        return cart.getCount();
    }

    // ---------- Products ----------

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", products.findAll());
        return "products";
    }

    // ---------- Cart ----------

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id) {
        if (products.existsById(id)) {
            cart.add(id);
        }
        return "redirect:/";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cart.remove(id);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartLine> lines = buildLines();
        model.addAttribute("lines", lines);
        model.addAttribute("total", total(lines));
        return "cart";
    }

    // ---------- Checkout ----------

    @PostMapping("/checkout")
    public String checkout(@RequestParam String customerName, @RequestParam String address) {
        List<CartLine> lines = buildLines();
        if (lines.isEmpty()) {
            return "redirect:/cart";
        }

        CustomerOrder order = new CustomerOrder();
        order.setCustomerName(customerName);
        order.setAddress(address);
        for (CartLine line : lines) {
            order.getItems().add(new OrderItem(
                    line.getProduct().getName(), line.getProduct().getPrice(), line.getQuantity()));
        }
        order.setTotal(total(lines));
        orders.save(order);
        cart.clear();
        return "redirect:/orders/" + order.getId();
    }

    // ---------- Orders ----------

    @GetMapping("/orders")
    public String orderList(Model model) {
        model.addAttribute("orders", orders.findAllByOrderByCreatedAtDesc());
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        CustomerOrder order = orders.findById(id).orElseThrow();
        model.addAttribute("order", order);
        return "order";
    }

    // ---------- Helpers ----------

    private List<CartLine> buildLines() {
        List<CartLine> lines = new ArrayList<>();
        cart.getItems().forEach((productId, qty) ->
                products.findById(productId).ifPresent(p -> lines.add(new CartLine(p, qty))));
        return lines;
    }

    private BigDecimal total(List<CartLine> lines) {
        return lines.stream().map(CartLine::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
