package fuzzycsv

import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: kay
 * Date: 10/20/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
class FuzzyCSVTableTest {

    static def csv2 = [
            ['sub_county', 'ps_total_score', 'pipes_total_score', 'tap_total_score'],
            ['Hakibale', 18.1, null, null],
            ['Kabonero', 1, null, null],
            ['Kisomoro', null, 1, 10],
            ['Bunyangabu', null, null, '1'],
            ['Noon', null, null, 0]
    ]

    @Test
    void testAggregate() {
        def data = FuzzyCSVTable.get(Data.csv).aggregate(['sub_county'], new Sum(columns: ['ps_total_score', 'pipes_total_score', 'tap_total_score'], columnName: 'sum'))
        def expected = [
                ['sub_county', 'sum'],
                ['Hakibale', 31.1]
        ]
        assert data.csv == expected
    }

    @Test
    void testAggregate2Columns() {
        def data = FuzzyCSVTable.get(Data.csv).aggregate(['sub_county'], new Sum(columns: ['ps_total_score', 'pipes_total_score'], columnName: 'sum'),
                new Sum(columns: ['tap_total_score'], columnName: 'sum_taps'))
        def expected = [
                ['sub_county', 'sum', 'sum_taps'],
                ['Hakibale', 20.1, 11]
        ]
        assert data.csv == expected
    }

    @Test
    void testAggregateAggregator() {
        def data = FuzzyCSVTable.get(Data.csv).aggregate(['sub_county'],
                CompositeAggregator.get('avg',
                        [
                                new Sum(['ps_total_score', 'pipes_total_score'], 'sum'),
                                new Sum(['tap_total_score'], 'sum_taps')
                        ]
                        , { it.sum_taps.value + it.sum.value }))
        def expected = [
                ['sub_county', 'avg'],
                ['Hakibale', 31.1]
        ]
        assert data.csv == expected
    }
}
