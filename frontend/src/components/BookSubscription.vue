<template>
    <div>
        <String
            label="Name"
            v-model="value.name"
            :editMode="editMode"
        />
        <Number
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <Boolean
            label="IsBookSubscribed"
            v-model="value.isBookSubscribed"
            :editMode="editMode"
        />
        <Date
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
    name: 'BookSubscription',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "bookSubscriptions",
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
