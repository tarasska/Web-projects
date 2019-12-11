<template>
    <div class="post list">
        <div v-for="post in viewPosts" v-bind:key="post.id">
            <Post :users="users" :post="post" :comments="comments" :isSinglePost="false"/>
        </div>
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        name: "Index",
        components: {Post},
        props: ['users', 'posts', 'comments'],
        comments: {
            Post
        },
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        }
    }
</script>

<style scoped>

</style>
