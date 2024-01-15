<!--
 * @Author: LoveKamila 120189976+LoveKamila@users.noreply.github.com
 * @Date: 2023-09-19 00:08:37
 * @LastEditors: LoveKamila 120189976+LoveKamila@users.noreply.github.com
 * @LastEditTime: 2023-09-19 01:16:17
 * @FilePath: \TongXin-Community-Font-End-main\src\components\searchuser.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
    <el-select v-model="query.type" placeholder="查找方式" class="handle-select mr10">
        <el-option key="1" label="ID" value="ID"></el-option>
        <el-option key="2" label="用户名" value="用户名"></el-option>
    </el-select>
    <el-input v-model="query.content" placeholder="" class="handle-input mr10"></el-input>
    <el-button type="" :icon="Search" @click="handleSearch">搜索</el-button>
</template>

<script setup lang="ts">
import { ref, reactive, defineEmits, defineComponent } from 'vue';
import { Search } from '@element-plus/icons-vue';

const query = reactive({
    type: '',
    content: ''
});

const emit = defineEmits<{
  (e: 'search', query: {type: string, content: string}): void
}>();

const handleSearch = () => {
    emit("search", query);
}

</script>


<style scoped>

</style>