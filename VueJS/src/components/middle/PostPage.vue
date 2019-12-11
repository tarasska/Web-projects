<template>
    <div class="PostPage">
        <Post :users="users" :post="post"/>
        <div v-for="comment in currentComments(post)" v-bind:key="comment.id">
            <div class="comment">
                <div class="header"> {{users[comment.userId].name}}</div>
                <div class="body"> {{comment.text}}</div>
            </div>
        </div>
    </div>
</template>

<script>
    import Post from "./Post";

    export default {
        name: "PostPage",
        props: ['users', 'comments', 'post'],
        components: {
            Post
        },
        methods: {
            viewPost: function (post) {
                this.$root.$emit("onViewPost", post);
            },
            currentComments: function (post) {
                return Object.values(this.comments).filter(c => post.id === c.postId);
            }
        }
    }
</script>

<style scoped>
    .comment {
        position: relative;
        border: 1px solid var(--border-color);
        border-radius: var(--border-radius);
        margin-top: 0.25rem;
        padding-top: 0.5rem;
        padding-left: 0.5rem;
        min-height: max-content;
        min-width: 90%;
        overflow: hidden;
    }

    .comment .header {
        margin-top: 0.25rem;
        font-size: 0.85rem;
        color: #888;
    }

    .comment .body {
        padding-bottom: 0.5rem;
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    }
</style>