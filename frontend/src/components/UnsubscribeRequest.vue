<template>

    <v-card outlined>
        <v-card-title>
            UnsubscribeRequest
        </v-card-title>

        <v-card-text>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
            <String label="Name" v-model="value.name" :editMode="editMode"/>
            <Boolean label="IsSubscribed" v-model="value.isSubscribed" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="unsubscribeRequest"
            >
                UnsubscribeRequest
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'UnsubscribeRequestCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.id = 0;
        this.value.name = '';
        this.value.isSubscribed = false;
    },
    watch: {
    },
    methods: {
        unsubscribeRequest() {
            this.$emit('unsubscribeRequest', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

