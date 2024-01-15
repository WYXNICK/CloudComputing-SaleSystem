<template>
    <div class="box">
        <el-select v-model="query.type" placeholder="查找方式" style="width: 10%;margin-right: 20px;">
            <el-option key="1" label="ID" value="ID"></el-option>
            <el-option key="2" label="日期" value="日期"></el-option>
            <el-option key="3" label="帖文内容" value="帖文内容"></el-option>
        </el-select>
        <el-input v-model="query.content" placeholder="" class="handle-input mr10" style="width: 60%;margin-right: 20px;"></el-input>
        <el-button type="" :icon="Search" @click="handleSearch" style="width: 7%;margin-right: 20px;">搜索</el-button>
        
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, defineEmits, defineComponent } from 'vue';
import { Search, Clock } from '@element-plus/icons-vue';

const query = reactive({
    type: '',
    content: ''
});

const emit = defineEmits<{
    (e: 'search', query: { type: string, content: string }): void
    (e: 'reset'):void
}>();

const handleSearch = () => {
    emit("search", query);
}


</script>


<style scoped>

.box {
    display: inline;
}
</style>