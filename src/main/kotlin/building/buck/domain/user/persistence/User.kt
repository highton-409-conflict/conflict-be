package building.buck.domain.user.persistence

import building.buck.domain.collection.persistence.Collection
import building.buck.domain.post.persistence.Post
import building.buck.domain.post.persistence.PostLike
import building.buck.domain.timeline.persistence.Timeline
import building.buck.domain.topic.persistence.TopicSubscription
import building.buck.global.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tbl_user")
class User(
    @Column(nullable = false, unique = true)
    val accountId: String,

    var name: String,

    @Column(nullable = false)
    var password: String,

    @Column(columnDefinition = "TEXT")
    var introduce: String? = null,

    @Column
    var profile: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "representative_collection_id")
    var representativeCollection: Collection? = null
) : BaseEntity() {

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var timeline: Timeline? = null

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var posts: MutableList<Post> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var collections: MutableList<building.buck.domain.collection.persistence.Collection> = mutableListOf()

    @OneToMany(mappedBy = "follower", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var followings: MutableList<UserFollowing> = mutableListOf()

    @OneToMany(mappedBy = "following", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var followers: MutableList<UserFollowing> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var likes: MutableList<PostLike> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var subscriptions: MutableList<TopicSubscription> = mutableListOf()
}
