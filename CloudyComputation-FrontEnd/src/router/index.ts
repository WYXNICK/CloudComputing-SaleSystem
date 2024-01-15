import { createRouter, createWebHashHistory, RouteRecordRaw,createWebHistory } from 'vue-router';
import { usePermissStore } from '../store/permiss';
import Home from '../views/home.vue';

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/product-mng-pg',
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/sales-mng-pg',
                name: 'salesManagement',
                meta: {
                    title: '销售管理',
                    permiss: '20',
                },
                component: () => import(/* webpackChunkName: "table" */ '../views/SalesPage.vue'),
            },
            {
                path: '/user-mng-pg',
                name: 'userManagement',
                meta: {
                    title: '用户管理',
                    permiss: '20',
                },
                component: () => import(/* webpackChunkName: "table" */ '../views/CustomerPage.vue'),
            },
            {
                path: '/product-mng-pg',
                name: 'productManagement',
                meta: {
                    title: '商品管理',
                    permiss: '20',
                },
                component: () => import(/* webpackChunkName: "table" */ '../views/ProductPage.vue'),
            },
          
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});


export default router;
