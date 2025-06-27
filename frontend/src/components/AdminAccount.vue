<template>
    <div>
        <String
            label="Email"
            v-model="value.email"
            :editMode="editMode"
        />
        <String
            label="Password"
            v-model="value.password"
            :editMode="editMode"
        />
        <String
            label="Roles"
            v-model="value.roles"
            :editMode="editMode"
        />
        <String
            label="CreatedAt"
            v-model="value.createdAt"
            :editMode="editMode"
        />
        <String
            label="UpdatedAt"
            v-model="value.updatedAt"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'AdminAccount',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "adminAccounts",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
