package vn.hoidanit.laptopshop.domain.dto;

import java.util.List;
import java.util.Optional;

public class ProductCriteriaDTO {
    private Optional<String> name;
    private Optional<String> page;
    private Optional<List<String>> factories;
    private Optional<List<String>> targets;
    private Optional<List<String>> price;
    private Optional<String> sort;

    public ProductCriteriaDTO() {
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<String> getPage() {
        return page;
    }

    public void setPage(Optional<String> page) {
        this.page = page;
    }

    public Optional<List<String>> getFactories() {
        return factories;
    }

    public void setFactories(Optional<List<String>> factories) {
        this.factories = factories;
    }

    public Optional<List<String>> getTargets() {
        return targets;
    }

    public void setTargets(Optional<List<String>> targets) {
        this.targets = targets;
    }

    public Optional<List<String>> getPrice() {
        return price;
    }

    public void setPrice(Optional<List<String>> price) {
        this.price = price;
    }

    public Optional<String> getSort() {
        return sort;
    }

    public void setSort(Optional<String> sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ProductCriteriaDTO{" +
                "name=" + name +
                ", page=" + page +
                ", factories=" + factories +
                ", targets=" + targets +
                ", price=" + price +
                ", sort=" + sort +
                '}';
    }
}
