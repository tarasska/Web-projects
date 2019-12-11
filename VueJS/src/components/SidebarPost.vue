<template>
    <section>
        <div class="header">
            By {{users[post.userId].login}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a href="#page=Post" @click="viewPost(post)">View all</a>
        </div>
    </section>
</template>

<script>
    export default {
        props: ['users', 'post'],
        name: "SidebarPost",
        methods: {
            viewPost: function (post) {
                this.$root.$emit("onViewPost", post);
            }
        }
    }
</script>

<style scoped>

</style>
