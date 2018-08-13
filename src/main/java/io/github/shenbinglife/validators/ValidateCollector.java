package io.github.shenbinglife.validators;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验结果收集器
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
public class ValidateCollector implements ValidateResult {

    /**
     * 被校验对象
     */
    private ValidatedTarget target;

    /**
     * 校验结果
     */
    private List<ValidateResult> results;

    public ValidateCollector(ValidatedTarget target) {
        this.target = target;
        this.results = new ArrayList<>();
    }

    public ValidateCollector(ValidateResult result) {
        this.target = result.getTarget();
        this.results = new ArrayList<>();
        results.add(result);
    }

    /**
     * 收集校验结果
     * 
     * @param result 校验结果
     */
    public void collect(ValidateResult result) {
        this.results.add(result);
    }

    /**
     * 获取所有的基础校验结果
     * 
     * @return 基础校验结果集合
     */
    public List<SimpleValidateResult> getSimpleValidateResults() {
        List<SimpleValidateResult> list = new ArrayList<>(16);
        for (ValidateResult result : results) {
            if (result instanceof SimpleValidateResult) {
                list.add((SimpleValidateResult) result);
            } else if (result instanceof ValidateCollector) {
                list.addAll(((ValidateCollector) result).getSimpleValidateResults());
            } else {
                throw new IllegalArgumentException("不支持收集的校验结果对象：" + result);
            }
        }
        return list;
    }

    @Override
    public ValidatedTarget getTarget() {
        return target;
    }

    public void setTarget(ValidatedTarget target) {
        this.target = target;
    }

    public List<ValidateResult> getResults() {
        return results;
    }

    public void setResults(List<ValidateResult> results) {
        this.results = results;
    }

    @Override
    public boolean isPass() {
        return results.stream().allMatch(ValidateResult::isPass);
    }
}
