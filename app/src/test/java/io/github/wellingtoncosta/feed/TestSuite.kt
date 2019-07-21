package io.github.wellingtoncosta.feed

import io.github.wellingtoncosta.feed.domain.interactor.PostInteractorTest
import io.github.wellingtoncosta.feed.infrastructure.repository.PostRepositoryTest
import io.github.wellingtoncosta.feed.infrastructure.repository.UserRepositoryTest
import io.github.wellingtoncosta.feed.app.ui.viewmodel.ListPostsViewModelTest
import io.github.wellingtoncosta.feed.infrastructure.api.CommentApiTest
import io.github.wellingtoncosta.feed.infrastructure.api.PostApiTest
import io.github.wellingtoncosta.feed.infrastructure.api.UserApiTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    ListPostsViewModelTest::class,
    PostInteractorTest::class,
    PostRepositoryTest::class,
    UserRepositoryTest::class,
    PostApiTest::class,
    CommentApiTest::class,
    UserApiTest::class
)
class TestSuite
