<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="query.type" placeholder="查找类型" style="flex:2">
                    <el-option key="1" label="顾客ID" value="customerId" @click="query.content= ''"></el-option>
                    <el-option key="2" label="顾客名" value="name"  @click="query.content= ''"></el-option>
                    <el-option key="3" label="手机号" value="phone"  @click="query.content= ''"></el-option>
                </el-select>
                <el-input v-if="query.type != 'time'" v-model="query.content" style="flex: 10"></el-input>
                <el-date-picker v-if="query.type == 'time'" v-model="query.content" type="date" placeholder="选择您要查找的日期" :size="size2" style="flex:10" />
                <el-button type="" :icon="Search" @click="performSearch" style="flex: 1">搜索</el-button>
                <el-button type="" :icon="Clock" @click="resetSearch" style="flex: 1;margin-left: 0;">重置</el-button>
                <el-button type="" :icon="Refresh" @click="pullData" style="flex: 1;margin-left: 0;">刷新</el-button>
            </div>
            <div>
                <el-select v-model="query.sequence" style="margin-bottom: 20px; float:right;"  @change="pullData">
                    <el-option key="1" label="默认" value="default" ></el-option>
                    <el-option key="2" label="按订单数量排序" value="order" ></el-option>
                    <el-option key="3" label="按消费金额排序" value="price" ></el-option>
                </el-select>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="customerId" min-width="150" label="顾客ID" align="center"></el-table-column>
                <el-table-column prop="name" min-width="130" label="顾客名" align='center'></el-table-column>
                <el-table-column prop="phone" min-width="130" label="手机号" align='center'></el-table-column>
                <el-table-column prop="order" v-if="query.sequence === 'order'" min-width="130" label="进行订单数量" align='center'></el-table-column>
                <el-table-column prop="price"  v-if="query.sequence === 'price'" min-width="130" label="消费总金额" align='center'></el-table-column>

            </el-table>

            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>

        </div>
    </div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { Delete, Edit, Search, Plus, User, Refresh, SuccessFilled, CircleCloseFilled, Document, Clock } from '@element-plus/icons-vue';
import txcRequest from '../service/index.js';

// 表格中的项目
interface TableItem {
    customerId: number,
    name: string,
    phone: string,
    order: number,
    price: number,
}

const size = 25;
const size2 = ref<'default' | 'large' | 'small'>('default')

const query = reactive({
    pageIndex: 1,
    pageSize: size,
    type: '',
    content: '',
    sequence: 'default',

});


const tableData = ref<Array<TableItem>>([]);
const pageTotal = ref(0);


const pullData = () => {
    txcRequest.request({
        url: '/customer/getAll',
        method: 'GET',
        params: {
            pageIndex: query.pageIndex,
            pageSize: query.pageSize,
            type: query.type,
            keyword: query.content,
            sequence: query.sequence
        }
    }
    ).then((res: any) => {
        if (!res.status) {
            console.log("hi");
            tableData.value = res.data;
            pageTotal.value = res.pageNum;

            for(let i = 0; i < tableData.value.length; i++){
                tableData.value[i].price = parseFloat(tableData.value[i].price.toFixed(2));
                console.log(tableData.value[i].price);
            }
        }
    }).catch((err: any) => {
        console.error(err);
    })
}

const performSearch = () => {
    txcRequest.request({
        url: '/customer/getAll',
        method: 'GET',
        params: {
            pageIndex: 1,
            pageSize: query.pageSize,
            type: query.type,
            keyword: query.content,
            sequence:  query.sequence
        }
    }).then((res: any) => {
        if (!res.type) {
            query.pageIndex = 1;
            tableData.value = res.data;
            pageTotal.value = res.pageNum;
            for(let i = 0; i < tableData.value.length; i++){
                tableData.value[i].price = parseFloat(tableData.value[i].price.toFixed(2));
                console.log(tableData.value[i].price);
            }
        }
    }).catch((err: any) => {
        console.error(err);
    })
}

const resetSearch = () => {
    query.pageIndex = 1;
    query.type = "";
    query.content = "";
    pullData();
}

pullData();

const handlePageChange = (val: number) => {
    query.pageIndex = val;
    pullData();
}



</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
    display: flex;
    flex-direction: row;
    gap: 20px;
}

.handle-select {
    width: 120px;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #F56C6C;
}

.green {
    color: #5cbf63
}

.blue {
    color: rgb(63, 158, 255)
}

.mr10 {
    margin-right: 10px;
}

.image-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    gap: 10px;
}

.tag-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    gap: 3px;
}

.table-td-thumb {
    width: calc(33.33% - 10px);
    height: auto;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}

.table-title {
    text-align: center;
}

:deep(.el-form-item__label) {
    font-weight: 600;
}


:deep() .el-popper {
    max-width: 40% !important;
}

.el-button+.el-button {
    margin-left: 0px;
}
</style>

