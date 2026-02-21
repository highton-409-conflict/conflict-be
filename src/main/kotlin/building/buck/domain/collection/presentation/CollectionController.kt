package building.buck.domain.collection.presentation

import building.buck.domain.collection.presentation.dto.req.CreateCollectionReq
import building.buck.domain.collection.presentation.dto.req.SelectCollectionReq
import building.buck.domain.collection.presentation.dto.res.CollectionRes
import building.buck.domain.collection.service.CreateCollectionService
import building.buck.domain.collection.service.GetMyCollectionsService
import building.buck.domain.collection.service.GetSelectedCollectionService
import building.buck.domain.collection.service.SelectCollectionService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/collections")
class CollectionController(
    private val createCollectionService: CreateCollectionService,
    private val selectCollectionService: SelectCollectionService,
    private val getMyCollectionsService: GetMyCollectionsService,
    private val getSelectedCollectionService: GetSelectedCollectionService
) {
    @Operation(summary = "본인의 컬렉션 목록 조회")
    @GetMapping("/my")
    fun getMyCollections(): List<CollectionRes>? {
        return getMyCollectionsService.execute()
    }

    @Operation(summary = "현재 선택된 컬렉션 조회")
    @GetMapping("/selected")
    fun getSelectedCollection(): CollectionRes? {
        return getSelectedCollectionService.execute()
    }

    @Operation(summary = "컬렉션 생성")
    @PostMapping
    fun createCollection(@RequestBody req: CreateCollectionReq): CollectionRes {
        return createCollectionService.execute(req)
    }

    @Operation(summary = "컬렉션 선택")
    @PutMapping("/select")
    fun selectCollection(@RequestBody req: SelectCollectionReq) = selectCollectionService.execute(req)
}
